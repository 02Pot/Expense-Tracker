import DeleteButton from '@/components/button/DeleteButton';
import React from 'react';
import { useTable } from 'react-table';
import './TransactionHistory.css';

function TransactionHistory({transactions,onDelete}){
    
    const columns = React.useMemo(
        () => [
            { Header: 'Category', accessor: 'category' },
            { Header: 'Description', accessor: 'description' },
            { Header: 'Date', accessor: 'date' },
            { Header: 'Type', accessor: 'type' },
            { Header: 'Amount', accessor: 'amount' },
            {
                Header: 'Action',
                accessor: 'id',
                Cell: ({ cell }) => {
                    const transaction = transactions.find(t => t.id === cell.value);
                    return (
                        <DeleteButton onDelete={() => onDelete(transaction.id,transaction.type)}/>
                    );
                }
            }
        ],
        [transactions]
    );

    const tableInstance = useTable({ columns, data:transactions });
    const { getTableProps, getTableBodyProps, headerGroups, rows, prepareRow } = tableInstance;

    return(
            <div className='transaction-history'>
                <div className='history-title'>
                    <p>Transaction History</p>
                </div>
                <div className='history-table'>
                    <table {...getTableProps()}>
                        <thead>
                            {headerGroups.map(headerGroup => {
                                const { key, ...restHeaderGroupProps } = headerGroup.getHeaderGroupProps();
                                return (
                                    <tr key={key} {...restHeaderGroupProps}>
                                        {headerGroup.headers.map(column => {
                                            const { key, ...restColumnProps } = column.getHeaderProps();
                                            return (
                                                <th key={key} {...restColumnProps}>
                                                    {column.render('Header')}
                                                </th>
                                            );
                                        })}
                                    </tr>
                                );
                            })}
                        </thead>
                        <tbody {...getTableBodyProps()}>
                            {rows.map(row => {
                                prepareRow(row);
                                const { key, ...restRowProps } = row.getRowProps();
                                return (
                                    <tr key={key} {...restRowProps}>
                                        {row.cells.map(cell => {
                                            const { key, ...restCellProps } = cell.getCellProps();
                                            return (
                                                <td key={key} {...restCellProps}>
                                                    {cell.render('Cell')}
                                                </td>
                                            );
                                        })}
                                    </tr>
                                );
                            })}
                        </tbody>
                    </table>
                </div>
            </div>
    );
}
export default TransactionHistory