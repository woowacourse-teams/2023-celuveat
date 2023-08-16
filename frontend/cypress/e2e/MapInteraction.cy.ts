describe('지도 상호작용 테스트', () => {
  it('음식점 카드 호버 시 지도에 있는 해당 마커가 강조된다.', () => {
    const restaurants = ['몽드샬롯', '대성연탄갈비', '미타우동', '식도원'];

    cy.visit(Cypress.config().baseUrl);
    cy.wrap(restaurants).each(restaurant => {
      cy.getBySel(`${restaurant} 카드`).trigger('mouseover');
      cy.getBySel(`${restaurant} 마커`).should('have.css', 'border').and('include', 'rgb(235, 152, 45)');
    });
  });

  it('음식점 마커 클릭 시 레스토랑 카드가 마커근처에 생성된다.', () => {
    cy.visit(Cypress.config().baseUrl);
    cy.getBySel('스시이도 오코노미 마커').click();
    cy.getBySel('스시이도 오코노미 오버레이').find('[data-cy="스시이도 오코노미 카드"]').should('exist');
  });
});
