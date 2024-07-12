const path = require('path');

module.exports = {
    mode: 'development',
    entry: {
        'openfeature-client-side': './src/pages/openfeature-client-side/index.js',
        'before-hook-email-crypto': './src/pages/before-hook-email-crypto/index.ts',
    },
    output: {
        path: path.resolve(__dirname, '../resources/dist/js'),
        filename: '[name].bundle.js'
    },
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
                test: /\.tsx?$/,
                use: 'ts-loader',
                exclude: /node_modules/,
            },
            {
                test: /\.css$/,
                use: ['style-loader', 'css-loader']
            },
        ]
    },
    resolve: {
        extensions: ['.tsx', '.ts', '.js'], // 添加.ts和.tsx作為可解析的擴展
    },
    devtool: 'source-map',
    watch: true,
    watchOptions: {
        ignored: '**/node_modules',
    },
};