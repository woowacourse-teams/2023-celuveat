declare namespace Cypress {
  interface Chainable {
    getByCy(selector: string, ...args): Chainable;
    getByAriaLabel(selector: string, ...args): Chainable;
    shouldBeList(restaurantNames: string[]): Chainable;
  }
}
