describe('좋아요 관련 기능을 테스트 한다.', () => {
  beforeEach(() => {
    cy.visit('/restaurants/311?celebId=7');
  });

  it('성시경 소문난성수감자탕 페이지에서 좋아요를 한 후 위시리스트에 잘 담겨 있는지 확인한다.', () => {
    // 로그인이 되지 않은 상태에서 위시리스트 저장하기를 누른다.
    cy.contains('위시리스트에 저장하기').click();

    cy.loginGoogleForDesktop();

    // 위시리스트 버튼을 다시 누른다.
    cy.contains('위시리스트에 저장하기').click();

    cy.get('button[aria-label="로그인"]').click(); // 프로필 아이콘을 누른다.
    cy.get('li[data-name="위시리스트"]').click(); // 위시리스트 버튼을 누른다.

    // 좋아요를 취소한다.
    cy.get('li[aria-label="소문난성수감자탕 카드"]').find('button').click();
    cy.get('li[aria-label="소문난성수감자탕 카드"]').find('button').find('svg').should('have.attr', 'fill', '#000');
  });
});
