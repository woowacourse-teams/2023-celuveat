describe('로그인 관련 로직을 테스트 한다.', () => {
  it('로그인 - 로그아웃 테스트', () => {
    cy.viewport('iphone-5');
    cy.visit('');

    /** 로그인에 성공하면 마이페이지에 접근할 수 있다. */
    cy.get('nav').find('button').last().click();
    cy.get('#root').should('contain.text', '비회원으로 이용하기');
    cy.get('#root').should('contain.text', '카카오로 로그인하기');
    cy.get('#root').should('contain.text', '구글로 로그인하기');
    cy.get('button[type="google"]').click();
    cy.wait(5000);

    cy.get('nav').find('button').last().click();
    cy.get('#root').should('contain.text', '로그아웃');

    /** 로그아웃이 되었다면 프로필 버튼 클릭시 로그인 페이지로 이동한다. */
    cy.contains('로그아웃').click();
    cy.get('nav').find('button').last().click();
    cy.get('#root').should('contain.text', '비회원으로 이용하기');
    cy.get('#root').should('contain.text', '카카오로 로그인하기');
    cy.get('#root').should('contain.text', '구글로 로그인하기');
  });
});
