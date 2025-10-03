CREATE TYPE
  utils.enum_s_song_c_tonalidade
AS ENUM (
  -- tonalidades maiores
  'C', 'C#',
  'D', 'D#',
  'E',
  'F', 'F#',
  'G', 'G#',
  'A', 'A#',
  'B',

  -- tonalidades menores
  'Am', 'A#m',
  'Bm',
  'Cm', 'C#m',
  'Dm', 'D#m',
  'Em',
  'Fm', 'F#m',
  'Gm', 'G#m'
);