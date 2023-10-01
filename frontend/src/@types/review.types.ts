export type ReviewFormType = 'create' | 'update' | 'delete' | 'report' | 'all' | null;

export type ReviewSubmitButtonType = Exclude<ReviewFormType, 'all' | 'delete' | 'report' | null>;
