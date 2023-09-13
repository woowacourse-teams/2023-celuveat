const productionConfig = require('./webpack.prod.js');
const developmentConfig = require('./webpack.dev.js');
const localConfig = require('./webpack.local.js');

module.exports = (env, args) => {
  const { TARGET_ENV } = env;

  switch (TARGET_ENV) {
    case 'msw':
      return localConfig;
    case 'dev':
      return developmentConfig;
    case 'prod':
      return productionConfig;
    default:
      throw new Error('No matching configuration was found!');
  }
};
