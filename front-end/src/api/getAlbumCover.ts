// services/itunes.ts
export async function getAlbumCover(artist: string, album: string): Promise<string | null> {
  try {
    const query = encodeURIComponent(`${artist} ${album}`);
    const url = `https://itunes.apple.com/search?term=${query}&entity=album&limit=1`;

    const response = await fetch(url);
    const data = await response.json();

    if (data.results && data.results.length > 0) {
      // artworkUrl100 → substituímos por resolução maior (600x600 ou até 1200x1200)
      return data.results[0].artworkUrl100.replace("100x100", "600x600");
    }

    return null;
  } catch (error) {
    console.error("Erro ao buscar capa:", error);
    return null;
  }
}
