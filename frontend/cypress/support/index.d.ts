declare namespace Cypress {
  interface Chainable {
    getByCy(selector: string, ...args): Chainable;
    getByAriaLabel(selector: string, ...args): Chainable;
    shouldBeList(restaurantNames: string[]): Chainable;

    dataCy(value: string): Chainable<JQuery<HTMLElement>>;

    /**
     * Custom command that adds two given numbers
     */
    asyncAdd(a: number, b: number): Chainable<number>;
    loginGoogle(): Chainable;
    loginGoogleForDesktop(): Chainable;
    loginGoogleForMobile(): Chainable;
    shouldIsLiked(restaurantName: string, isLiked: boolean): Chainable;
  }
}
