import EmailHasher from "./EmailHasher";

export function setupEventHandlers() {
    const userEmailInput = document.getElementById('userEmailInput');
    const userEmailHashPreview = document.getElementById('userEmailHashPreview');

    // 即時更新 Hash Preview：監聽 userEmailInput 的輸入事件
    userEmailInput.addEventListener('input', async function() {
        const email = userEmailInput.value.trim();
        if (email) {
            userEmailHashPreview.value = await EmailHasher.hashEmail(email);
        } else {
            userEmailHashPreview.value = ''; // 清空 Hash 值預覽
        }
    });

    // 是否前往第二步：監聽 userEmailInputBtn 的點擊事件
    document.getElementById('userEmailInputBtn').addEventListener('click', function() {
        const userEmailInputValue = userEmailInput.value;

        const evalOption = document.getElementById('evalOption');
        const evalResult = document.getElementById('evalResult');
        if (userEmailInputValue.trim() !== '') {
            evalOption.style.display = 'block';
            evalResult.style.display = 'none';
        } else {
            console.log('userEmailInputBtn Listener: userEmailInput is empty.');
            evalOption.style.display = 'none';
            evalResult.style.display = 'none';
        }
    });

    // 重設頁面：監聽 evalOptionSubmitBtn 的點擊事件
    document.getElementById('evalOptionResetBtn').addEventListener('click', function() {
        const evalOption = document.getElementById('evalOption');
        const evalResult = document.getElementById('evalResult');
        evalOption.style.display = 'none';
        evalResult.style.display = 'none';
        document.getElementById('userEmailInput').value = 'someone@example.com';
    });

    // 送往Server-side進行評估：監聽 serverSideEvalBtn 的點擊事件
    document.getElementById('serverSideEvalBtn').addEventListener('click', function() {
        const evalOutcome = document.querySelector('input[name="evalOutcome"]:checked').value;
        const userEmailInput = document.getElementById('userEmailInput').value;

        fetch('api/v1/flag-eval/before-hook-email-crypto', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                userEmailInput: userEmailInput,
                evalOutcome: evalOutcome })
        })
            .then(response => response.json())
            .then(data => console.log(data))
            .catch(error => console.error('Error:', error));
    });
}
