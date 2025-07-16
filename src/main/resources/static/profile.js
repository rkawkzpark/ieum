document.addEventListener('DOMContentLoaded', () => {

    const token = localStorage.getItem('accessToken');

    if (!token) {
        alert('로그인이 필요합니다.');
        window.location.href = 'login.html';
        return;
    }

    const displayNameElem = document.getElementById('display-name');
    const displayIntroElem = document.getElementById('display-intro');

    const loadUserProfile = async () => {
        try {
            const response = await fetch('/api/v1/users/me', {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${token}`
                }
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

    loadUserProfile();
});