
	package com.alphatrader.javagui.estimation;

	import com.alphatrader.rest.Company;
	import com.alphatrader.rest.Position;

	import java.util.Collections;
	import java.util.HashMap;
	import java.util.List;
	import java.util.Map;

	/**
	 * Fundamental estimator that evaluates the overall value of a company but does not include the own company assets
	 *
	 * @author frangelo
	 * @version 1.0
	 */
	class CleanFundValue extends Estimator {
	    private static final int ITERATIONS = 10;

	    private Map<String, Double> securityEstimation = new HashMap<>();

	    @Override
	    public void refresh(List<Company> companies) {
	       this.companies = companies;
	        securityEstimation = new HashMap<>();
	        this.companies.stream().forEach(company -> {
	            Double value = company
	                .getPortfolio()
	                .getPositions()
	                .stream()
	                .map(Position::getVolume)
	                .reduce(0.0, (a, b) -> (a + b));
	            securityEstimation.put(company.getListing().getSecurityIdentifier(), value / company.getProfile().getOutstandingShares());
	        });
	        
	    }

	    @Override
	    public Double evaluate(Company company) {
	        Double myReturn = null;

	        // If the company we are looking for doesn't exist in the map, we return Double.NaN.
	        if (!securityEstimation.containsKey(company.getListing().getSecurityIdentifier())) {
	            myReturn = Double.NaN;
	        } else {
	            myReturn = securityEstimation.get(company.getListing().getSecurityIdentifier());
	        }

	        return myReturn;
	    }
	    

	    private void recursiveEvaluation() {
	        for (int i = 0; i < ITERATIONS; i++) {
	            companies.forEach(company -> {
	                Double value = company
	                    .getPortfolio()
	                    .getPositions()
	                    .stream()
	                    .map(
	                        position -> { 
	                        	if (company.getListing().getSecurityIdentifier().equals(position.getSecurityIdentifier()))
	                        	{
	                        		return 0.0;
	                        	}
	                        	
	                            if (securityEstimation.containsKey(position.getSecurityIdentifier())) {
	                                return securityEstimation.get(position.getSecurityIdentifier()) * position.getNumberOfShares();
	                            } 
	                            else {
	                                return 0.0;
	                            }
	                            
	                        }
	                    )
	                    .reduce(0.0, (a, b) -> (a + b));

	                value /= (double) company.getProfile().getOutstandingShares();
	                securityEstimation.put(company.getListing().getSecurityIdentifier(), value);
	            });
	        }
	    }

	    /**
	     * @return a read-only list of company estimations
	     */
	    public Map<String, Double> getSecurityEstimations() {
	        return Collections.unmodifiableMap(securityEstimation);
	    }
	}



