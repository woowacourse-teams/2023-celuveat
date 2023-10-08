export const getClickedIcon = (pathname: string) => {
  const splitPathname = pathname.split('/');
  if (splitPathname[1] === '') return 'home';
  if (splitPathname[1] === 'map') return 'map';
  if (splitPathname[1] === 'signup') return 'user';
  return null;
};
