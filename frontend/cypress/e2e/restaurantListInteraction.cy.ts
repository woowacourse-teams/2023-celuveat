describe('음식점 리스트 상호작용 테스트', () => {
  it('현재 지도 바운더리 내의 음식점이 리스트에 생성된다.', () => {
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

    cy.intercept(
      'GET',
      `${Cypress.env(
        'apiUrl',
      )}/api/restaurants?lowLatitude=37.4526109976426&highLatitude=37.57787843528734&lowLongitude=127.04205511118164&highLongitude=127.16393468881836`,
      { fixture: 'restaurants' },
    );
    cy.visit(Cypress.config().baseUrl);
    cy.shouldBeList(restaurantNames);
  });

  it('선택한 카테고리에 해당하는 음식점이 리스트에 생성된다.', () => {
    const categoryToExpectedRestaurants = [
      [
        '일식당',
        ['동양', '냠냠물고기 2호점', '스시이도 오코노미', '스시아오마츠', '텐지몽', '숙성회장', '스시한다', '스시렌'],
      ],
      ['한식', ['식도원', '맛좋은순대국', '고흥선어회맛집', '7th Door', '산과바다']],
      ['와인', ['우오보 파스타 바']],
    ];

    cy.visit(Cypress.config().baseUrl);
    cy.wrap(categoryToExpectedRestaurants).each((item: [string, string[]]) => {
      cy.get(`[data-label='${item[0]}']`).click();
      cy.shouldBeList(item[1]);
    });
  });

  it('검색창에 주소 입력시 해당 주소를 중심좌표로 리스트가 생성된다.', () => {
    const addressToExpectedRestaurants = [
      ['대구', ['오늘도한우']],
      ['분당', ['종로영풍갈비', '스시야']],
      ['경주', ['영양숯불갈비']],
    ];

    cy.wrap(addressToExpectedRestaurants).each((item: [string, string[]]) => {
      cy.visit(Cypress.config().baseUrl);
      cy.getBySel('지역 검색').type(item[0]).wait(1000).type('{enter}');
      cy.shouldBeList(item[1]);
    });
  });
});
