import { useCallback, useEffect, useState } from 'react';
import { regex } from 'zod';
import debounce from 'lodash.debounce'

const CEP_REGEX = /^\d{8}$/;

type Endereco = {
    logradouro: string;
    bairro: string;
    localidade: string;
    estado: string;
    cep: string;
    uf: string;
}

type UseCepValidationReturn = {
    handleCepChange: (text: string)  => void;
    endereco: Endereco|null;
    loading: boolean;
    error: string|null;
    cep: string;
}

export const  useCepValidation = (): UseCepValidationReturn => {
    const [cep, setCep] = useState('');
    const [endereco, setEndereco] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const fetchCepData = async (cepValue) => {
        const cepLimpo = cepValue.replace(/\D/g, '');
        if(!cepLimpo) {
            setError(null);
            setEndereco(null);
            setLoading(false);
            return;
        }

        if (!CEP_REGEX.test(cepLimpo)) {
            setError("Formato de CEP inválido.");
            setEndereco(null);
            setLoading(false);
            return;
        }

        setLoading(true);
        setError(null);
        setEndereco(null);

        try {
            const response = await fetch(`https://viacep.com.br/ws/${cepLimpo}/json/`);
            const data = await response.json();

            if(data.erro) {
                setError("CEP não encontrado.");
            } else {
                setEndereco(data);
            }
        } catch (e) {
            setError("Não foi possível consltar o CEP. Verifique sua conexão.");
        } finally {
            setLoading(false);
        }
    }
    
    const debouncedFetch = useCallback(debounce(fetchCepData, 500), []);

    const handleCepChange = (text) => {
        setCep(text);
        setLoading(true);
        debouncedFetch(text);
    }

    useEffect(() => {
        return () => {
            debouncedFetch.cancel();
        }
    }, [debouncedFetch]);

    return { handleCepChange, endereco, loading, error, cep };
}