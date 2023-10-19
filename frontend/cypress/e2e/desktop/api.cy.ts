describe('API 테스트', () => {
  it('성시경이 다녀간 음식점들을 찾고 로그인 시 내가 좋아요한 음식점이 표시된다.', () => {
    cy.intercept(
      'GET',
      'https://d.api.celuveat.com/restaurants?lowLatitude=32&highLatitude=40&lowLongitude=120&highLongitude=132&sort=like&celebId=7',
    ).as('celebRestaurantsPage1');

    cy.intercept(
      'GET',
      'https://d.api.celuveat.com/restaurants?lowLatitude=32&highLatitude=40&lowLongitude=120&highLongitude=132&sort=like&celebId=7&page=1',
    ).as('celebRestaurantsPage2');

    cy.visit('/celeb/7');

    cy.contains('성시경 추천 맛집').should('be.visible');

    cy.wait(['@celebRestaurantsPage1', '@celebRestaurantsPage2']).then(interception => {
      const responseData = [...interception[0].response.body.content, ...interception[1].response.body.content];

      cy.get('li[data-cy="음식점 카드"]').then($restaurants => {
        $restaurants.map((i, $restaurant) => {
          expect($restaurant).to.have.contain(responseData[i].name);
        });
      });
    });

    cy.get('button[aria-label="로그인"]').click(); // 프로필 아이콘을 누른다.
    cy.get('li[data-name="로그인"]').click(); // 로그인 버튼을 누른다.

    cy.loginGoogleForDesktop();

    cy.shouldIsLiked('오띠젤리 카드', true);
  });

  it('압구정에 있는 음식점들을 찾고 로그인 시 내가 좋아요한 음식점이 표시된다.', () => {
    cy.intercept('GET', 'https://d.api.celuveat.com/main-page/region?codes=1168011000,1168010400&page=0').as(
      'regionRestaurantsPage1',
    );

    cy.intercept('GET', 'https://d.api.celuveat.com/main-page/region?codes=1168011000,1168010400&page=1').as(
      'regionRestaurantsPage2',
    );

    cy.visit('/region/apgujeong');

    cy.contains('압구정,청담 맛집').should('be.visible');

    cy.wait(['@regionRestaurantsPage1', '@regionRestaurantsPage2']).then(interception => {
      const responseData = [...interception[0].response.body.content, ...interception[1].response.body.content];

      cy.get('li[data-cy="음식점 카드"]').then($restaurants => {
        $restaurants.map((i, $restaurant) => {
          expect($restaurant).to.have.contain(responseData[i].name);
        });
      });
    });

    cy.get('button[aria-label="로그인"]').click(); // 프로필 아이콘을 누른다.
    cy.get('li[data-name="로그인"]').click(); // 로그인 버튼을 누른다.

    cy.loginGoogleForDesktop();

    cy.shouldIsLiked('엘픽 카드', true);
  });

  it('카테고리가 "한식"인 음식점들을 찾고 로그인 시 내가 좋아요한 음식점이 표시된다.', () => {
    const encodedUrl = encodeURIComponent('한식');

    cy.intercept(
      'GET',
      `https://d.api.celuveat.com/restaurants?lowLatitude=32&highLatitude=40&lowLongitude=120&highLongitude=132&sort=like&category=${encodedUrl}`,
    ).as('categoryRestaurantsPage1');

    cy.intercept(
      'GET',
      `https://d.api.celuveat.com/restaurants?lowLatitude=32&highLatitude=40&lowLongitude=120&highLongitude=132&sort=like&category=${encodedUrl}&page=1`,
    ).as('categoryRestaurantsPage2');

    cy.visit(`/category/${encodedUrl}`);

    cy.contains('한식').should('be.visible');

    cy.wait(['@categoryRestaurantsPage1', '@categoryRestaurantsPage2']).then(interception => {
      const responseData = [...interception[0].response.body.content, ...interception[1].response.body.content];

      cy.get('li[data-cy="음식점 카드"]').then($restaurants => {
        $restaurants.map((i, $restaurant) => {
          expect($restaurant).to.have.contain(responseData[i].name);
        });
      });
    });

    cy.get('button[aria-label="로그인"]').click(); // 프로필 아이콘을 누른다.
    cy.get('li[data-name="로그인"]').click(); // 로그인 버튼을 누른다.

    cy.loginGoogleForDesktop();

    cy.shouldIsLiked('청실홍실 카드', true);
  });
});
