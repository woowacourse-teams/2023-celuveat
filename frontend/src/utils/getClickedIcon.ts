export const getClickedIcon = (pathname: string) => {
  const [, splitPathname] = pathname.split('/');
  if (splitPathname === '') return 'home';
  if (splitPathname === 'map') return 'map';
  if (splitPathname === 'signup') return 'user';
  return null;
};
