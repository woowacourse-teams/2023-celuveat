// cypress/support/commands.ts

Cypress.Commands.add('getByCy', (selector, ...args) => cy.get(`[data-cy='${selector}']`, ...args));
Cypress.Commands.add('getByAriaLabel', (selector, ...args) => cy.get(`[aria-label='${selector}']`, ...args));

Cypress.Commands.add('shouldBeList', restaurantNames => {
  restaurantNames.forEach(name => {
    cy.getByAriaLabel('음식점 리스트').find(`[data-cy="${name} 카드"]`).should('exist');
  });
});

Cypress.Commands.add('shouldIsLiked', (restaurantName, isLiked) => {
  cy.getByAriaLabel(restaurantName)
    .find('button')
    .find('svg')
    .should('have.attr', 'fill', isLiked ? 'red' : '#000');
});

Cypress.Commands.add('loginGoogle', () => {
  cy.origin('https://accounts.google.com', () => {
    Cypress.on(
      'uncaught:exception',
      err => !err.message.includes('ResizeObserver loop') && !err.message.includes('Error in protected function'),
    );

    cy.get('input[type="email"]').type(Cypress.env('GOOGLE_EMAIL_FOR_TESTING'));
    cy.contains('다음').click().wait(20000);

    cy.get('[type="password"]').type(Cypress.env('GOOGLE_PASSWORD_FOR_TESTING'));
    cy.get('다음').click().wait(20000);
  });
});

Cypress.Commands.add('loginGoogleForDesktop', () => {
  cy.getByAriaLabel('프로필').click();
  cy.getByAriaLabel('로그인').click();
  cy.contains('구글로 로그인하기').click();
});

Cypress.Commands.add('loginGoogleForMobile', () => {
  cy.get('nav').find('button').last().click(); // 모바일 nav 하단의 마이 페이지 버튼을 누른다.
  cy.get('button[type="google"]').click(); // 구글 로그인 하기 버튼을 누른다.

  cy.loginGoogle();
});

Cypress.on('uncaught:exception', (err, runnable) => {
  // returning false here prevents Cypress from failing the test
  return false;
});
