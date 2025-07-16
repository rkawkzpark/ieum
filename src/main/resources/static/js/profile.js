document.addEventListener('DOMContentLoaded', () => {

    // 1. 필요한 DOM 요소들을 모두 가져옵니다.
    const profileView = document.getElementById('profile-view');
    const profileEditForm = document.getElementById('profile-edit-form');
    const editBtn = document.getElementById('edit-btn');

    const displayNameElem = document.getElementById('display-name');
    const displayIntroElem = document.getElementById('display-intro');

    const inputName = document.getElementById('input-name');
    const inputIntro = document.getElementById('input-intro');

    // 2. 토큰 확인
    const token = localStorage.getItem('accessToken');
    if (!token) {
        alert('로그인이 필요합니다.');
        window.location.href = 'login.html';
        return;
    }

    // 3. 프로필 정보를 조회하고 화면에 표시하는 함수 정의
    const loadUserProfile = async () => {
        try {
            const response = await fetch('/api/v1/users/me', {
                method: 'GET',
                headers: { 'Authorization': `Bearer ${token}` }
            });

            if (response.ok) {
                const responseData = await response.json();
                const userProfile = responseData.data;
                displayNameElem.textContent = userProfile.name;
                displayIntroElem.textContent = userProfile.introduction || '(자기소개가 없습니다)';
            } else {
                alert('사용자 정보를 불러오는데 실패했습니다. 다시 로그인해주세요.');
                localStorage.clear();
                window.location.href = 'login.html';
            }
        } catch (error) {
            console.error('프로필 정보 조회 중 오류 발생:', error);
            alert('오류가 발생했습니다. 관리자에게 문의해주세요.');
        }
    };

    // 4. '수정하기' 버튼에 이벤트 리스너 연결
    editBtn.addEventListener('click', () => {
        // 현재 화면 정보를 수정 폼에 미리 채워넣기
        inputName.value = displayNameElem.textContent;
        const introText = displayIntroElem.textContent;
        inputIntro.value = introText === '(자기소개가 없습니다)' ? '' : introText;

        // UI 전환
        profileView.style.display = 'none';
        profileEditForm.style.display = 'flex';
    });

    // 5. 페이지가 처음 로드될 때 프로필 조회 함수를 실행
    loadUserProfile();
});