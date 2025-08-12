// profile.js

document.addEventListener('DOMContentLoaded', () => {

    // --- DOM 요소 가져오기 ---
    const profileView = document.getElementById('profile-view');
    const profileEditForm = document.getElementById('profile-edit-form');
    const editBtn = document.getElementById('edit-btn');
    const displayNameElem = document.getElementById('display-name');
    const displayIntroElem = document.getElementById('display-intro');
    const inputName = document.getElementById('input-name');
    const inputIntro = document.getElementById('input-intro');
    const container = document.querySelector('.container');

    // --- 인증 및 API 통신 ---

    /**
     * 인증 토큰을 포함하여 fetch 요청을 보내는 래퍼 함수
     * 401 Unauthorized 에러 발생 시 로그인 페이지로 리디렉션
     * @param {string} url - 요청할 URL
     * @param {object} options - fetch 옵션
     * @returns {Promise<Response>} - fetch 응답 Promise
     */
    const fetchWithAuth = async (url, options = {}) => {
        const token = localStorage.getItem('accessToken');
        if (!token) {
            alert('로그인이 필요합니다.');
            window.location.href = 'login.html';
            return;
        }

        const headers = {
            ...options.headers,
            'Authorization': `Bearer ${token}`
        };

        const response = await fetch(url, { ...options, headers });

        if (response.status === 401) {
            const responseData = await response.json();
            // 서버에서 보낸 에러 메시지를 우선적으로 사용
            const errorMessage = responseData.error?.message || '인증에 실패했습니다. 다시 로그인해주세요.';
            alert(errorMessage);
            localStorage.clear(); // 만료된 토큰 정보 삭제
            window.location.href = 'login.html';
            return;
        }

        return response;
    };


    // --- UI 업데이트 관련 함수 ---

    /**
     * 로딩 상태 UI를 표시하는 함수
     * @param {boolean} isLoading - 로딩 상태 여부
     */
    const showLoading = (isLoading) => {
        let loadingElement = document.getElementById('loading-indicator');
        if (isLoading) {
            if (!loadingElement) {
                loadingElement = document.createElement('p');
                loadingElement.id = 'loading-indicator';
                loadingElement.textContent = '로딩 중...';
                container.prepend(loadingElement);
            }
            profileView.style.display = 'none';
            profileEditForm.style.display = 'none';
        } else {
            if (loadingElement) {
                loadingElement.remove();
            }
            profileView.style.display = 'block';
        }
    };

    /**
     * 사용자 프로필 정보를 화면에 표시하는 함수
     * @param {object} userProfile - 사용자 프로필 데이터 { name, introduction }
     */
    const updateProfileView = (userProfile) => {
        displayNameElem.textContent = userProfile.name;
        displayIntroElem.textContent = userProfile.introduction || '(자기소개가 없습니다)';
    };

    /**
     * 수정 폼에 현재 프로필 정보를 채워넣는 함수
     */
    const populateEditForm = () => {
        const introText = displayIntroElem.textContent;
        inputName.value = displayNameElem.textContent;
        inputIntro.value = introText === '(자기소개가 없습니다)' ? '' : introText;
    };

    /**
     * 프로필 보기 모드와 수정 모드를 전환하는 함수
     * @param {'view' | 'edit'} mode - 표시할 모드
     */
    const switchMode = (mode) => {
        if (mode === 'edit') {
            populateEditForm();
            profileView.style.display = 'none';
            profileEditForm.style.display = 'flex';
        } else { // 'view'
            profileView.style.display = 'block';
            profileEditForm.style.display = 'none';
        }
    };


    // --- 데이터 처리 및 이벤트 핸들러 ---

    /**
     * 페이지 로드 시 사용자 프로필을 가져오는 메인 함수
     */
    const loadUserProfile = async () => {
        showLoading(true);
        try {
            const response = await fetchWithAuth('/api/v1/users/me');
            if (!response) return;

            if (response.ok) {
                const { data } = await response.json();
                updateProfileView(data);
            } else {
                // fetchWithAuth에서 401을 처리하므로, 여기서는 그 외의 에러를 처리
                const { error } = await response.json();
                alert(`사용자 정보를 불러오는데 실패했습니다: ${error.message}`);
            }
        } catch (error) {
            console.error('프로필 조회 중 네트워크 오류:', error);
            alert('네트워크 오류가 발생했습니다. 잠시 후 다시 시도해주세요.');
        } finally {
            showLoading(false);
        }
    };

    /**
     * '저장하기' 폼 제출 시 프로필을 수정하는 이벤트 핸들러
     */
    const handleProfileUpdate = async (event) => {
        event.preventDefault();
        const requestBody = {
            name: inputName.value,
            introduction: inputIntro.value
        };

        try {
            const response = await fetchWithAuth('/api/v1/users/me', {
                method: 'PATCH',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(requestBody)
            });
            if (!response) return;

            const responseData = await response.json();
            if (response.ok) {
                alert('성공적으로 수정되었습니다.');
                updateProfileView(responseData.data); // API 응답의 최신 데이터로 화면 업데이트
                switchMode('view');
            } else {
                alert(`수정에 실패했습니다: ${responseData.error?.message || '알 수 없는 오류'}`);
            }
        } catch (error) {
            console.error('프로필 수정 중 네트워크 오류:', error);
            alert('네트워크 오류가 발생했습니다. 잠시 후 다시 시도해주세요.');
        }
    };


    // --- 이벤트 리스너 연결 ---
    editBtn.addEventListener('click', () => switchMode('edit'));
    profileEditForm.addEventListener('submit', handleProfileUpdate);

    // --- 페이지 초기화 ---
    loadUserProfile();
});