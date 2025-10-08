// src/services/itunes.ts
export async function getAlbumCoverSafe(artist: string, album: string): Promise<string | null> {
  try {
    const query = encodeURIComponent(`${artist} ${album}`);
    const url = `https://itunes.apple.com/search?term=${query}&entity=album&limit=1`;

    const response = await fetch(url);
    const data = await response.json();

    if (data?.results && data.results.length > 0) {
      // pega artwork e aumenta resolução
      const artwork = String(data.results[0].artworkUrl100).replace("100x100", "600x600");

      // proxify + encode para garantir que o proxy receba a URL corretamente
      const proxied = `https://images.weserv.nl/?url=${encodeURIComponent(artwork)}`;

      return proxied;
    }

    return null;
  } catch (error) {
    console.error("Erro getAlbumCoverSafe:", error);
    return null;
  }
}
