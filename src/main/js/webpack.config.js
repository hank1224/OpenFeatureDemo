const path = require('path');
const fs = require('fs');

// 設定你的源代碼目錄
const srcDir = path.resolve(__dirname, './src');

// 讀取src目錄，為每個.js文件創建一個入口點
const entryPoints = fs.readdirSync(srcDir).reduce((entries, file) => {
    if (file.endsWith('.js')) {
        const entryKey = file.replace('.js', '');
        entries[entryKey] = path.join(srcDir, file);
    }
    return entries;
}, {});

module.exports = {
    entry: entryPoints,
    output: {
        filename: '[name].js',
        path: path.resolve(__dirname, '../../main/resources/static/js')
    },
    mode: 'production', // 或者 'development' 根據你的需求
};
