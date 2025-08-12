/**
 * 페이지에 공통 요소를 동적으로 불러와 삽입하는 함수
 * @param {string} placeholderId - 공통 요소를 삽입할 부모 요소의 ID
 * @param {string} filePath - 불러올 HTML 파일의 경로
 * @param {function} callback - 로드 완료 후 실행할 콜백 함수 (옵션)
 */
const loadComponent = (placeholderId, filePath, callback) => {
    const placeholder = document.getElementById(placeholderId);
    if (!placeholder) {
        console.warn(`'${placeholderId}' 요소를 찾을 수 없어 컴포넌트를 로드할 수 없습니다.`);
        return;
    }

    fetch(filePath)
        .then(response => {
            if (!response.ok) throw new Error(`${filePath} 로드 실패 (상태 코드: ${response.status})`);
            return response.text();
        })
        .then(html => {
            placeholder.innerHTML = html;
            if (callback) callback(); // 로드가 완료되면 콜백 함수 실행
        })
        .catch(error => {
            console.error('컴포넌트 로딩 오류:', error);
            // 에러 발생 시 사용자에게 보여줄 메시지를 수정합니다.
            placeholder.innerHTML = `<p style="text-align: center; color: red;">'${placeholderId}' 로딩 중 오류 발생. 경로를 확인해주세요.</p>`;
        });
};

document.addEventListener('DOMContentLoaded', () => {
    // 1. 헤더 로드
    // 헤더 파일 경로를 절대 경로로 수정합니다.
    loadComponent('header-placeholder', '/html/include/header.html', () => {
        const logoutBtn = document.getElementById('logout-btn');
        if (logoutBtn) {
            logoutBtn.addEventListener('click', () => {
                // auth.js에 정의될 전역 로그아웃 함수를 호출합니다.
                if (typeof handleLogout === 'function') {
                    handleLogout();
                } else {
                    console.error('handleLogout 함수를 찾을 수 없습니다. auth.js를 확인해주세요.');
                    alert('로그아웃 기능을 사용할 수 없습니다.');
                }
            });
        }
    });

    // 2. 푸터 로드 (나중에 푸터가 추가될 경우를 위한 예시)
    // loadComponent('footer-placeholder', '/html/include/footer.html');
});