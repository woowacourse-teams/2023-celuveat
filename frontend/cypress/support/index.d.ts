declare namespace Cypress {
  interface Chainable {
    getBySel(selector: string, ...args): Chainable;
    shouldBeList(restaurantNames: string[]): Chainable;
  }
}
