// main.js - 인증 가드

document.addEventListener('DOMContentLoaded', () => {
    const token = localStorage.getItem('accessToken');

    if (!token) {
        alert('로그인이 필요한 페이지입니다.');
        // 사용자를 로그인 페이지로 리디렉션합니다.
        window.location.href = '/html/login.html';
    }
});