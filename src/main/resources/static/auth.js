document.addEventListener('DOMContentLoaded', () => {

    // --- 공통 헬퍼 함수 ---

    // 모든 에러 메시지를 지우는 함수
    const clearErrorMessages = (form) => {
        form.querySelectorAll('.error-message').forEach(p => p.textContent = '');
    };

    // 특정 필드에 에러 메시지를 표시하는 함수
    const displayFieldError = (form, field, message) => {
        const errorElement = form.querySelector(`[name="${field}"] + .error-message`);
        if (errorElement) {
            errorElement.textContent = message;
        }
    };

    // API 요청 및 응답 처리를 위한 공통 함수
    const handleFormSubmit = async (form, endpoint, successCallback) => {
        form.addEventListener('submit', async (event) => {
            event.preventDefault();
            clearErrorMessages(form);

            const formData = new FormData(form);
            const requestBody = Object.fromEntries(formData.entries());

            try {
                const response = await fetch(endpoint, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(requestBody),
                });

                const responseData = await response.json();

                if (response.ok) {
                    successCallback(responseData.data);
                } else {
                    const errorInfo = responseData.error;
                    console.error('API Error:', errorInfo); // 디버깅을 위해 에러 정보 전체를 콘솔에 출력

                    // 1. 유효성 검사 에러 처리 (INVALID_INPUT_VALUE)
                    if (errorInfo && errorInfo.code === 'INVALID_INPUT_VALUE' && errorInfo.errors) {
                        errorInfo.errors.forEach(err => displayFieldError(form, err.field, err.reason));
                    }
                    // 2. 그 외 백엔드에서 정의된 모든 종류의 에러 처리
                    else if (errorInfo && errorInfo.message) {
                        alert(errorInfo.message); // ex: "인증에 실패했습니다."
                    }
                    // 3. 예상치 못한 형태의 에러일 경우
                    else {
                        alert('알 수 없는 오류가 발생했습니다. 개발자 콘솔을 확인해주세요.');
                    }
                }
            } catch (error) {
                console.error('Fetch error:', error);
                alert('서버와 통신 중 오류가 발생했습니다.');
            }
        });
    };


    // --- 페이지별 기능 초기화 ---

    // 로그인 페이지 기능 초기화
    const emailLoginForm = document.getElementById('email-login-form');
    const studentIdLoginForm = document.getElementById('student-id-login-form');

    if (emailLoginForm && studentIdLoginForm) {
        // 로그인 성공 시 실행될 콜백 함수
        const handleLoginSuccess = (data) => {
            localStorage.setItem('accessToken', data.accessToken);
            localStorage.setItem('refreshToken', data.refreshToken);
            alert('로그인 성공!');
            window.location.href = '/'; // 메인 페이지로 이동 (지금은 없으므로 루트로 설정)
        };

        // 각 로그인 폼에 공통 핸들러 연결
        handleFormSubmit(emailLoginForm, '/api/v1/auth/login/email', handleLoginSuccess);
        handleFormSubmit(studentIdLoginForm, '/api/v1/auth/login/student-id', handleLoginSuccess);

        // 탭 전환 기능
        const emailLoginTabBtn = document.getElementById('email-login-tab-btn');
        const studentIdLoginTabBtn = document.getElementById('student-id-login-tab-btn');
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

    // 회원가입 페이지 기능 초기화
    const signupForm = document.getElementById('signup-form');
    if (signupForm) {
        handleFormSubmit(signupForm, '/api/v1/users/sign-up', () => {
            alert('회원가입이 성공적으로 완료되었습니다! 로그인 페이지로 이동합니다.');
            window.location.href = 'login.html';
        });
    }

});