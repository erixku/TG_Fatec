CREATE TYPE
  utils.s_song_e_tonalidade
AS ENUM (
  -- tonalidades maiores
  'c', 'c#',
  'd', 'd#',
  'e',
  'f', 'f#',
  'g', 'g#',
  'a', 'a#',
  'b',

  -- tonalidades menores
  'am', 'a#m',
  'bm',
  'cm', 'c#m',
  'dm', 'd#m',
  'em',
  'fm', 'f#m',
  'gm', 'g#m'
);