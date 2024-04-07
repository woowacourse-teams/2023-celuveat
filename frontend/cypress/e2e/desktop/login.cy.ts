describe('로그인 관련 로직을 테스트 한다.', () => {
  it('로그인 - 새로고침 - 로그아웃 테스트', () => {
    cy.visit('');

    cy.getByAriaLabel('프로필').click();
    /** 로그인 전에는 마이 페이지 탭이 존재하지 않는다. */
    cy.getByCy('dropdown').should('not.contain.text', '마이 페이지');
    cy.getByAriaLabel('로그인').click();
    cy.contains('구글로 로그인하기').click();

    /** 로그인에 성공했다면 마이 페이지 탭이 존재한다. */
    cy.getByAriaLabel('프로필').click();
    cy.getByCy('dropdown').should('contain.text', '마이 페이지');

    /** 새로고침을 하더라도 로그인 상태는 유지된다. */
    cy.reload();
    cy.getByAriaLabel('프로필').click();
    cy.getByCy('dropdown').should('contain.text', '마이 페이지');

    /** 로그아웃 이후 프로필 클릭시 로그인 탭만 존재한다. */
    cy.getByCy('dropdown').contains('마이 페이지').click();
    cy.contains('로그아웃').click();
    cy.wait(1000);
    cy.getByAriaLabel('프로필').click();
    cy.getByCy('dropdown').should('contain.text', '로그인');
    cy.getByCy('dropdown').should('not.contain.text', '마이 페이지');
  });
});
