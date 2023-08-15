describe('지도 상호작용 테스트', () => {
  it('음식점 카드 호버 시 지도에 있는 해당 마커가 강조된다.', () => {
    const restaurants = ['몽드샬롯', '대성연탄갈비', '미타우동', '식도원'];

    cy.visit(Cypress.config().baseUrl);
    cy.wrap(restaurants).each(restaurant => {
      cy.getBySel(`${restaurant} 카드`).trigger('mouseover');
      cy.getBySel(`${restaurant} 마커`).should('have.css', 'border').and('include', 'rgb(235, 152, 45)');
    });
  });
});
