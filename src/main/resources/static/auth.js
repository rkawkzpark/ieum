    document.addEventListener('DOMContentLoaded', () => {

        const emailLoginTabBtn = document.getElementById('email-login-tab-btn');
        const studentIdLoginTabBtn = document.getElementById('student-id-login-tab-btn');
        const emailLoginForm = document.getElementById('email-login-form');
        const studentIdLoginForm = document.getElementById('student-id-login-form');

        // null 체크
        if (emailLoginTabBtn && studentIdLoginTabBtn && emailLoginForm && studentIdLoginForm) {

            // 이메일 로그인
            emailLoginTabBtn.addEventListener('click', () => {
                emailLoginForm.style.display = 'block';
                studentIdLoginForm.style.display = 'none';

                // 이메일은 active, 학번은 제거
                emailLoginTabBtn.classList.add('active');
                studentIdLoginTabBtn.classList.remove('active');
            });

            // 학번 로그인
            studentIdLoginTabBtn.addEventListener('click', () => {
                emailLoginForm.style.display = 'none';
                studentIdLoginForm.style.display = 'block';

                // 학번은 active, 이메일은 제거
                studentIdLoginTabBtn.classList.add('active');
                emailLoginTabBtn.classList.remove('active');
            });
        }
    });