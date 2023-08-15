Cypress.Commands.add('getBySel', (selector, ...args) => cy.get(`[data-cy='${selector}']`, ...args));

Cypress.Commands.add('shouldBeList', restaurantNames => {
  restaurantNames.forEach(name => {
    cy.getBySel('음식점 리스트').should('contain', name);
  });
});
