const path = require('path');
// const HtmlWebpackPlugin = require('html-webpack-plugin');

module.exports = {
    mode: 'development',
    // 定義多個入口
    entry: {
        'openfeature-client-side': './src/pages/openfeature-client-side/index.js',
    },

    // 輸出配置
    output: {
        path: path.resolve(__dirname, '../resources/dist/js'),  // 輸出目錄
        filename: '[name].bundle.js'  // 使用入口名稱作為文件名
    },

    // plugins: [
    //     new HtmlWebpackPlugin({
    //         template: './src/main/resources/templates/openfeature-client-side.html', // 指定模板文件
    //         filename: 'openfeature-client-side.html',
    //         inject: true
    //     })
    // ],

    // 模塊解析規則
    module: {
        rules: [
            {
                test: /\.js$/,
                exclude: /node_modules/,
                use: {
                    loader: 'babel-loader',
                    options: {
                        presets: ['@babel/preset-env']
                    }
                }
            },
            {
                test: /\.css$/,
                use: ['style-loader', 'css-loader']
            },
            // 可以添加更多的loader，例如處理圖片、字體等
        ]
    },

    // 開發工具
    devtool: 'source-map',

    // 監視模式
    watch: true,
    watchOptions: {
        ignored: '**/node_modules',
    },
};