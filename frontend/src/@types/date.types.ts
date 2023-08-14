type Digit = 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 0;

type FromOneToNine = Exclude<Digit, 0>;

type Year = `20${Digit}${Digit}` | `21${Digit}${Digit}`;

type Month = `0${FromOneToNine}` | `1${0 | 1 | 2}`;

type Day = `0${FromOneToNine}` | `${1 | 2}${Digit}` | `3${0 | 1}`;

export type DateSplitHyphen = `${Year}-${Month}-${Day}`;
