Cypress.Commands.add('getBySel', (selector, ...args) => cy.get(`[data-cy='${selector}']`, ...args));

Cypress.Commands.add('shouldBeList', restaurantNames => {
  restaurantNames.forEach(name => {
    cy.getBySel('음식점 리스트').find(`[data-cy="${name} 카드"]`).should('exist');
  });
});
