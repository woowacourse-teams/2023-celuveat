export const isEqual = (targetA: string | number, targetB: string | number) => targetA === targetB;

export const isEmptyString = (target: string) => target.length === 0;

export const isEmptyList = (target: unknown[]) => target.length === 0;

export const isMoreThan = (target: number, standard: number) => target > standard;
