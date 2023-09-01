describe('음식점 리스트 상호작용 테스트', () => {
  it('선택한 카테고리에 해당하는 음식점이 리스트에 그려지고, 음식점이 존재하지 않는 경우 "일치하는 음식점이 없어요."라는 문구를 가진 화면을 보여준다.', () => {
    const categories = [
      '일식당',
      '한식',
      '와인',
      '초밥,롤',
      '생선회',
      '양식',
      '와인',
      '육류,고기요리',
      '이자카야',
      '돼지고기구이',
      '요리주점',
    ];

    cy.visit(Cypress.config().baseUrl);
    cy.wrap(categories).each((category: string) => {
      cy.get(`[data-label='${category}']`).click();
      cy.getByCy('음식점 리스트').then($restaurantList => {
        if ($restaurantList.find('[data-cy="음식점 카드"]').length) {
          cy.getByCy('음식점 카드')
            .should('exist')
            .each(restaurantCard => {
              expect(restaurantCard.text()).to.include(category);
            });
        } else {
          cy.getByCy('음식점 리스트').should('contain.text', '일치하는 음식점이 없어요.');
        }
      });
    });
  });
});
