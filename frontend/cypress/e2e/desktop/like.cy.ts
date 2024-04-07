describe('좋아요 관련 기능을 테스트 한다.', () => {
  const targetName = '소문난성수감자탕';

  beforeEach(() => {
    cy.visit('');

    cy.loginGoogleForDesktop();
    cy.getByAriaLabel(`${targetName} 카드`).first().click();
    cy.contains('위시리스트').click();
  });

  it('좋아요를 누른 음식점은 위시리스트에 저장된다.', () => {
    cy.getByAriaLabel('프로필').click();
    cy.getByCy('dropdown').contains('위시리스트').click();

    cy.get('ul').should('contain.text', targetName);
  });
});
