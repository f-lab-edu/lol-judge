import axios from 'axios';

export const member = {
    actions: {
        signup(params) {
            return new Promise(() => {
                axios.post('http://localhost:3000/signUp', params)
                    .catch(err => {
                        console.error(err);
                        if (err.code === 'ERR_NETWORK') {
                            alert('네트워크 오류가 발생하였습니다. 관리자에게 문의해주세요');
                        }
                    })
            });
        }
    }
}