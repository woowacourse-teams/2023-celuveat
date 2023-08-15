describe('리스트 상호작용 테스트', () => {
  it('현재 지도 바운더리 내의 음식점이 리스트에 생성된다.', () => {
    cy.intercept(
      'GET',
      `${Cypress.env(
        'apiUrl',
      )}/api/restaurants?lowLatitude=37.4526109976426&highLatitude=37.57787843528734&lowLongitude=127.04205511118164&highLongitude=127.16393468881836`,
      {
        fixture: 'restaurants',
      },
    );

    cy.visit(Cypress.config().baseUrl);

    const restaurantNames = [
      '바이킹스워프',
      '소피텔',
      '몽드샬롯',
      '대성연탄갈비',
      '미타우동',
      '형제상회 가락몰지점',
      '그랜드 워커힐 서울 더파빌리온',
      '산과바다',
    ];

    restaurantNames.forEach(name => {
      cy.getBySel('음식점 리스트').should('contain', name);
    });
  });
});
