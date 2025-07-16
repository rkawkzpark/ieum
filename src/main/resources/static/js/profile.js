document.addEventListener('DOMContentLoaded', () => {

    // --- DOM 요소 가져오기 ---
    const profileView = document.getElementById('profile-view');
    const profileEditForm = document.getElementById('profile-edit-form');
    const editBtn = document.getElementById('edit-btn');
    const displayNameElem = document.getElementById('display-name');
    const displayIntroElem = document.getElementById('display-intro');
    const inputName = document.getElementById('input-name');
    const inputIntro = document.getElementById('input-intro');

    const token = localStorage.getItem('accessToken');
    if (!token) {
        alert('로그인이 필요합니다.');
        window.location.href = '/html/login.html';
        return;
    }

    // --- 함수 정의 ---

    /** 프로필 정보 조회 및 화면 표시 함수 */
    const loadUserProfile = async () => {
        try {
            const response = await fetch('/api/v1/users/me', {
                method: 'GET',
                headers: { 'Authorization': `Bearer ${token}` }
            });

            if (response.ok) {
                const { data } = await response.json();
                displayNameElem.textContent = data.name;
                displayIntroElem.textContent = data.introduction || '(자기소개가 없습니다)';
            } else {
                alert('사용자 정보를 불러오는데 실패했습니다. 다시 로그인해주세요.');
                localStorage.clear();
                window.location.href = '/html/login.html';
            }
        } catch (error) {
            console.error('프로필 조회 오류:', error);
            alert('오류가 발생했습니다.');
        }
    };

    // --- 이벤트 리스너 연결 ---

    /** '수정하기' 버튼 클릭 이벤트 */
    editBtn.addEventListener('click', () => {
        const introText = displayIntroElem.textContent;

        inputName.value = displayNameElem.textContent;
        inputIntro.value = introText === '(자기소개가 없습니다)' ? '' : introText;

        profileView.style.display = 'none';
        profileEditForm.style.display = 'flex';
    });

    /** '저장하기' 폼 제출 이벤트 */
    profileEditForm.addEventListener('submit', async (event) => {
        event.preventDefault();

        const requestBody = {
            name: inputName.value,
            introduction: inputIntro.value
        };

        try {
            const response = await fetch('/api/v1/users/me', {
                method: 'PATCH',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
                body: JSON.stringify(requestBody)
            });

            if (response.ok) {
                alert('프로필이 성공적으로 수정되었습니다.');
                window.location.reload();
            } else {
                const { error } = await response.json();
                alert(error.message || '프로필 수정에 실패했습니다.');
            }
        } catch (error) {
            console.error('프로필 수정 오류:', error);
            alert('오류가 발생했습니다.');
        }
    });

    // --- 페이지 초기화 ---
    loadUserProfile();
});