CREATE TYPE
  utils.enum_s_song_c_tonalidade
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