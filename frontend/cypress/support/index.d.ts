declare namespace Cypress {
  interface Chainable {
    getBySel(selector: string): Chainable;
    shouldBeList(restaurantNames: string[]): Chainable;
  }
}
