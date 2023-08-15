Cypress.Commands.add('getBySel', (selector, ...args) => cy.get(`[data-cy='${selector}']`, ...args));
