document.addEventListener('DOMContentLoaded', () => {

    // 로그인 페이지
    const emailLoginTabBtn = document.getElementById('email-login-tab-btn');
    const studentIdLoginTabBtn = document.getElementById('student-id-login-tab-btn');
    const emailLoginForm = document.getElementById('email-login-form');
    const studentIdLoginForm = document.getElementById('student-id-login-form');

    if (emailLoginTabBtn && studentIdLoginTabBtn && emailLoginForm && studentIdLoginForm) {
        emailLoginTabBtn.addEventListener('click', () => {
            emailLoginForm.style.display = 'flex';
            studentIdLoginForm.style.display = 'none';
            emailLoginTabBtn.classList.add('active');
            studentIdLoginTabBtn.classList.remove('active');
        });
        studentIdLoginTabBtn.addEventListener('click', () => {
            emailLoginForm.style.display = 'none';
            studentIdLoginForm.style.display = 'flex';
            studentIdLoginTabBtn.classList.add('active');
            emailLoginTabBtn.classList.remove('active');
        });
    }

    // 회원가입 페이지
    const signupForm = document.getElementById('signup-form');

    if (signupForm) {
        const clearErrorMessages = () => {
            document.querySelectorAll('.error-message').forEach(p => p.textContent = '');
        };

        const displayFieldError = (field, message) => {
            const errorElement = document.querySelector(`[name="${field}"] + .error-message`);
            if (errorElement) {
                errorElement.textContent = message;
            }
        };

        signupForm.addEventListener('submit', async (event) => {
            event.preventDefault();
            clearErrorMessages();

            const formData = new FormData(signupForm);
            const requestBody = Object.fromEntries(formData.entries());

            try {
                const response = await fetch('/api/v1/users/sign-up', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(requestBody),
                });

                const responseData = await response.json();

                if (response.ok) {
                    alert('회원가입이 성공적으로 완료되었습니다! 로그인 페이지로 이동합니다.');
                    window.location.href = 'login.html';
                } else {
                    const errorInfo = responseData.error;
                    if (errorInfo && errorInfo.code === 'INVALID_INPUT_VALUE') {
                        errorInfo.errors.forEach(err => {
                            displayFieldError(err.field, err.reason);
                        });
                    } else if (errorInfo) {
                        alert(errorInfo.message || '알 수 없는 오류가 발생했습니다.');
                    } else {
                        alert('알 수 없는 오류가 발생했습니다.');
                    }
                }
            } catch (error) {
                console.error('Sign-up fetch error:', error);
                alert('서버와 통신 중 오류가 발생했습니다.');
            }
        });
    }
});