const path = require('path');

module.exports = {
  mode: process.env.NODE_ENV,
  entry: ['./src/index.tsx'],
  output: {
    path: path.resolve(__dirname, '../dist/'),
    publicPath: '/',
    filename: '[name].[chunkhash:8].js',
    clean: true,
  },
  resolve: {
    extensions: ['.tsx', '.ts', '.jsx', '.js', '.json'],
  },
  module: {
    rules: [
      {
        test: /\.(ts|tsx)$/,
        use: [
          'babel-loader',
          {
            loader: 'ts-loader',
            options: {
              transpileOnly: true,
            },
          },
        ],
        exclude: /node_modules/,
      },
      {
        test: /\.(jpg|jpeg|gif|png|ico)?$/,
        type: 'asset',
        generator: {
          filename: 'images/[name].[ext]',
        },
      },
      {
        test: /\.(woff|woff2|eot|ttf|otf)?$/,
        type: 'asset',
        generator: {
          filename: 'fonts/[name].[ext]',
        },
      },
      {
        test: /\.svg$/,
        use: ['@svgr/webpack'],
      },
    ],
  },

  devServer: {
    static: {
      directory: path.resolve(__dirname, '../public'),
    },
    hot: true,
    open: true,
    historyApiFallback: true,
    allowedHosts: 'all',
  },

  optimization: {
    minimize: true,
  },
};
