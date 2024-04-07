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

  it('좋아요를 취소하면 위시리스트에서 제거된다.', () => {
    cy.contains('위시리스트').click();
    cy.getByAriaLabel('프로필').click();
    cy.getByCy('dropdown').contains('위시리스트').click();

    cy.get('ul').should('not.contain.text', targetName);
  });

  it('좋아요를 누른 음식점을 위시리스트에서 좋아요를 해제해도 바로 제거되지 않는다. 즉 새로고침을 해야 제거된다.', () => {
    cy.getByAriaLabel('프로필').click();
    cy.getByCy('dropdown').contains('위시리스트').click();

    cy.getByAriaLabel(`${targetName} 카드`).find('[aria-label="좋아요"]').click();
    cy.get('ul').should('contain.text', targetName);

    cy.reload();

    cy.get('ul').should('not.contain.text', targetName);
  });
});
