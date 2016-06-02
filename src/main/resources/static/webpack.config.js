module.exports = {
    entry: "./scripts/main.js",
    output: {
        path: "./dist",
        filename: "bundle.js"
    },
    module: {
        loaders: [
            {
                test: '.hbs',
                loader: 'handlebars-loader'
            }
        ]
    }
};