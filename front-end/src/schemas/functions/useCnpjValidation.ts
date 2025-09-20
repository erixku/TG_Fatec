import { useState } from "react";

export default function useCnpjValidation() {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [data, setData] = useState<any>(null);

  async function validateCnpj(cnpj: string) {
    setLoading(true);
    setError(null);
    setData(null);

    try {
      const response = await fetch(`https://brasilapi.com.br/api/cnpj/v1/${cnpj}`);
      if (!response.ok) {
        throw new Error("CNPJ não encontrado ou inválido.");
      }
      const result = await response.json();
      setData(result);
      return result;
    } catch (err: any) {
      setError(err.message || "Erro ao validar CNPJ.");
      return null;
    } finally {
      setLoading(false);
    }
  }

  return { validateCnpj, loading, error, data };
}