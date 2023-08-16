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
    const restaurants = ['맛좋은순대국', '씨푸드박스', '스시이도 오코노미', '동양'];

    cy.wrap(restaurants).each(restaurant => {
      cy.visit(Cypress.config().baseUrl);
      cy.getBySel(`${restaurant} 마커`).click();
      cy.getBySel(`${restaurant} 오버레이`).find(`[data-cy='${restaurant} 카드']`).should('exist');
    });
  });

  it('다른 마커를 클릭하면 레스토랑 카드가 사라지고 새로운 레스토랑 카드가 생성된다', () => {
    cy.visit(Cypress.config().baseUrl);
    cy.getBySel('몽드샬롯 마커').click();
    cy.getBySel('몽드샬롯 오버레이').find('[data-cy="몽드샬롯 카드"]').should('exist');

    cy.getBySel('대성연탄갈비 마커').click();
    cy.getBySel('몽드샬롯 오버레이').find('[data-cy="몽드샬롯 카드"]').should('not.exist');
    cy.getBySel('대성연탄갈비 오버레이').find('[data-cy="대성연탄갈비 카드"]').should('exist');

    cy.getBySel('씨푸드박스 마커').click();
    cy.getBySel('대성연탄갈비 오버레이').find('[data-cy="대성연탄갈비 카드"]').should('not.exist');
    cy.getBySel('씨푸드박스 오버레이').find('[data-cy="씨푸드박스 카드"]').should('exist');
  });
});
