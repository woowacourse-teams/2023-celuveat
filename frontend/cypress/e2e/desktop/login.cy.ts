describe('로그인 관련 로직을 테스트 한다.', () => {
  it('사용자가 구글 로그인을 하면 비회원 상태이던 프로필 이미지가 구글 로그인 프로필 이미지로 변경된다.', () => {
    cy.visit('/restaurants/311?celebId=7');

    cy.getByAriaLabel('프로필').click(); // 프로필 아이콘을 누른다.
    cy.getByAriaLabel('로그인').click(); // 로그인 버튼을 누른다.
    cy.contains('구글로 로그인하기').click(); // 로그인 버튼을 누른다.

    // cy.loginGoogleForDesktop();

    // cy.get('button[aria-label="로그인"]').find('img').should('have.attr', 'alt', '푸만능 프로필');
  });
});
