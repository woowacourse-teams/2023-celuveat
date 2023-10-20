describe('로그인 관련 로직을 테스트 한다.', () => {
  beforeEach(() => {
    cy.viewport('iphone-5');

    cy.visit('/restaurants/311?celebId=7', {
      onBeforeLoad: (win: any) => {
        win.ontouchstart = true;
      },
    });

    // cy.get('nav').find('button').last().click(); // 모바일 nav 하단의 마이 페이지 버튼을 누른다.
    // cy.get('button[type="google"]').click(); // 구글 로그인 하기 버튼을 누른다.
  });

  it('모바일에서 성시경, 소문난성수감자탕 페이지에서 로그인을 한 후 다시 성시경, 소문난성수감자탕 페이지로 돌아 간다.', () => {
    cy.location().should(loc => {
      expect(loc.href).to.eq('http://localhost:3000');
    });
  });

  // it('모바일에서 성시경, 소문난성수감자탕 페이지에서 로그인을 하고 회원 탈퇴 시 비회원 상태가 된다.', () => {
  //   cy.get('nav').find('button').last().click(); // 모바일 nav 하단의 마이 페이지 버튼을 누른다.
  //   cy.contains('회원탈퇴').click().wait(5000);
  //   cy.get('button').contains('탈퇴하기').click();

  //   cy.get('nav').find('button').last().click(); // 모바일 nav 하단의 마이 페이지 버튼을 누른다.

  //   cy.contains('비회원으로 이용하기');
  // });
});
