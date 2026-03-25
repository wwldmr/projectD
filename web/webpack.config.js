const path = require('path')

module.exports = {
    mode: 'development',
    entry: './src/main.js',

    output: {
        path: path.resolve(__dirname, 'dist'),
        filename: 'bundle.js',
    },

    module: {
            rules: [
                {
                    test: /\.(js|jsx)$/,
                    exclude: /node_modules/,
                    use: {
                        loader: 'babel-loader',
                    },
                },
            ],
    },

    devServer: {
        static: './public',
        hot: true,
        port: 3000,
        proxy: [
            {
                context: ['/api', '/auth'],
                target: 'http://localhost:8081',
                changeOrigin: true,
            }
        ],
        historyApiFallback: true,
    },

    resolve: {
        extensions: ['.js', '.jsx'],
    }
}
