const user = {
    keyId: document.getElementById('keyId').value,
}

console.log('Getting EvalContent { keyId: ' + user.keyId + ' }' );

export const evalContent = {
    keyId: user.keyId,
}