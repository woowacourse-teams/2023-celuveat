Cypress.Commands.add('getByCy', (selector, ...args) => cy.get(`[data-cy='${selector}']`, ...args));
Cypress.Commands.add('getByAriaLabel', (selector, ...args) => cy.get(`[aria-label='${selector}']`, ...args));

Cypress.Commands.add('shouldBeList', restaurantNames => {
  restaurantNames.forEach(name => {
    cy.getByAriaLabel('음식점 리스트').find(`[data-cy="${name} 카드"]`).should('exist');
  });
});
